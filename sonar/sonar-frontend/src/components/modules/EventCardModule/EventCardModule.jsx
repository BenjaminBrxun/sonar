import * as React from 'react';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Button from '@mui/material/Button';
import ShareIcon from '@mui/icons-material/Share';
import BookmarkIcon from '@mui/icons-material/Bookmark';
import Snackbar from '@mui/material/Snackbar';
import MuiAlert from '@mui/material/Alert';

import "./EventCardModule.scss";
import {navigate} from "vike/client/router";
import {useState} from "react";
import {usePageContext} from "vike-react/usePageContext";


export default function EventCardModule({title, date, date_text, costs, image, restricted, id}) {
    const pageContext = usePageContext();
    const isFavoritesPage = pageContext.urlPathname.startsWith("/bookmarks");
    let bookmarked = localStorage.getItem("bookmarks")?.includes(id);
    console.log(bookmarked)
    // Diese Funktion codiert einen Base64 String wieder als Bilddatei
    // function dataToImage(data) {
    //     return Buffer.from(data, 'binary').toString('base64');
    // }

    function restrictedToString(restricted) {
        if (restricted) {
            return "Mit Anmeldung";
        } else {
            return "Ohne Anmeldung";
        }
    }

    function costsWithCurrency(costs) {
        return costs + " €";
    }

    function dateToDateSpan(date) {
        let dateFormat = new Date(date);
        let milliseconds = dateFormat.getTime();
        let today = new Date();
        let difference = today.getTime() - milliseconds;

        if (difference > 0) {
            return ["vergangen", "_past"]
        }

        if (difference <= 86400000 && difference >= -86400000) {
            return ["heute", "_today"]
        }

        if (difference < 0 && difference >= -259200000) {
            return ["in kürze", "_next"]
        }

        if (difference < -259200000) {
            return ["zukünftig", "_soon"]
        }
    }

    function showDetails() {
        navigate(`/event?eventId=${encodeURIComponent(id)}`);
    }

    //SHARE
    const [snackbarMessage, setSnackbarMessage] = useState("Link kopiert!");
    const [snackbarSeverity, setSnackbarSeverity] = useState("success");
    const [snackbarOpen, setSnackbarOpen] = useState(false);

    const handleCopy = async () => {
        try {
            await navigator.clipboard.writeText(`${window.location.origin}/event?eventId=${encodeURIComponent(id)}`);
            setSnackbarMessage("Link kopiert!");
            setSnackbarSeverity("success");
        } catch (err) {
            setSnackbarMessage("Fehler beim Kopieren");
            setSnackbarSeverity("error");
            console.error("Fehler beim Kopieren", err);
        }
        setSnackbarOpen(true);
    };

    const handleSnackbarClose = (_, reason) => {
        if (reason === 'clickaway') return;
        setSnackbarOpen(false);
    };

    const handleBookmarking = () => {
        let loggedIn = (localStorage.getItem("email") !== null);
        if (!loggedIn) {
            navigate("/profile")
        } else {
            let bookmarkedEvents = JSON.parse(localStorage.getItem("bookmarks"));
            console.log(bookmarkedEvents);
            if (bookmarkedEvents === null) bookmarkedEvents = []
            // Event zu Favoriten hinzufügen
            if (!bookmarkedEvents.includes(id)) {
                bookmarked = true;
                bookmarkedEvents.push(id);
                document.getElementById(id).className = "sonar-eventcard_top-icon bookmarked"
                localStorage.setItem("bookmarks", JSON.stringify(bookmarkedEvents));
                const link = "http://localhost:8081/api/v1/user/favourites/add?email=" + localStorage.getItem("email") + "&eventId=" + id;
                fetch(link, {method: "POST"})
                    .catch(err => console.log("Event konnte nicht gespeichert werden:" +
                        " " + err.message));
            } else {
                bookmarked = false;
                document.getElementById(id).className = "sonar-eventcard_top-icon"
                const newBookmarks = bookmarkedEvents.filter(ev => ev !== id);
                localStorage.setItem("bookmarks", JSON.stringify(newBookmarks));
                console.log(localStorage.getItem("bookmarks"));
                const link = "http://localhost:8081/api/v1/user/favourites/remove?email=" + localStorage.getItem("email") + "&eventId=" + id;
                fetch(link, {method: "POST"})
                    .catch(err => console.log("Event konnte nicht aus den Favoriten entfernt werden:" +
                        " " + err.message));

                if (isFavoritesPage) {
                    document.getElementById(id + "card").style.display = "none";
                }
            }
        }
        console.log(bookmarked);
    }

    function renderBookmarkedIcons() {
        if (bookmarked) {
            document.getElementById(id).className = "sonar-eventcard_top-icon bookmarked";
        }
    }

    return (
        <Card id={id + "card"} className="sonar-eventcard" onLoad={renderBookmarkedIcons}>
            <div className="sonar-eventcard_container">
                <div className="sonar-eventcard_media-div">
                    <CardMedia className="sonar-eventcard_media"
                               component="img"
                               alt="family"
                               image={image}
                    />
                    <div className="sonar-eventcard_header">
                        <CardActions className="sonar-eventcard_top-icons">
                            <div className="sonar-eventcard_top-icon">
                                <Button size="small" onClick={handleCopy}><ShareIcon/></Button>
                            </div>
                            <div className="sonar-eventcard_top-icon" id={id}>
                                <Button size="small" onClick={handleBookmarking}><BookmarkIcon/></Button>
                            </div>
                            <div id="sonar-eventcard_top-icon-buffer"></div>
                            <div className="sonar-eventcard_top-details">
                                <Button size="small" onClick={showDetails}>Details</Button>
                            </div>
                        </CardActions>
                        <CardActions className="sonar-eventcard_tags">
                            <div
                                className={"sonar-eventcard_tag-icon sonar-eventcard_tag-icon-date" + (dateToDateSpan(date)[1])}>
                                <label>{dateToDateSpan(date)[0]}</label>
                            </div>
                            <div
                                className={"sonar-eventcard_tag-icon sonar-eventcard_tag-icon-registration" + (restricted ? '_needed' : '')}>
                                <label>{restrictedToString(restricted)}</label>
                            </div>
                        </CardActions>
                    </div>
                </div>

                <div className="sonar-eventcard_content">

                    <CardContent id="sonar-eventcard_content_text-div">

                        <div className="sonar-eventcard_content_text">
                            <p className="sonar-eventcard_content_text-title">
                                {title}
                            </p>
                            <p className="sonar-eventcard_content_text-date">
                                Datum: {date_text}
                            </p>
                            <p className="sonar-eventcard_content_text-costs">
                                {costsWithCurrency(costs)}
                            </p>
                        </div>
                    </CardContent>
                </div>
            </div>
            <Snackbar
                open={snackbarOpen}
                autoHideDuration={3000}
                onClose={handleSnackbarClose}
                anchorOrigin={{vertical: 'bottom', horizontal: 'left'}}
            >
                <MuiAlert onClose={handleSnackbarClose} severity={snackbarSeverity} sx={{width: '100%'}}>
                    {snackbarMessage}
                </MuiAlert>
            </Snackbar>
        </Card>
    );
}
