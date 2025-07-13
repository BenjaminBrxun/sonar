import React, {useEffect, useState} from "react";
import "./DetailComponent.scss"
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Button from '@mui/material/Button';
import ShareIcon from '@mui/icons-material/Share';
import CancelIcon from '@mui/icons-material/Cancel';
import BookmarkIcon from '@mui/icons-material/Bookmark';
import Snackbar from '@mui/material/Snackbar';
import MuiAlert from '@mui/material/Alert';
import {navigate} from "vike/client/router";
import {dateToDateSpan, format_date_to_text, restrictedToString} from "../../modules/TagsAndTime/TagsAndTime.js";

export function DetailComponent({eventId}) {

    const [event, setEvent] = useState([]);
    let bookmarked = false;
    useEffect(() => {
        if (eventId !== null && eventId !== undefined) {
            fetch(`http://localhost:8081/api/v1/event/${eventId}`)
                .then(res => res.json())
                .then(data => {
                    document.getElementById("label-date").innerHTML = dateToDateSpan(data.startDate)[0];
                    document.getElementById("label-reg").innerHTML = restrictedToString(data.restricted);
                    console.log(data.categories);
                    document.getElementById("detail-sonar-eventcard_categorys").innerHTML = '';
                    data.categories.forEach((category) => {
                        console.table(category.name);
                        document.getElementById("detail-sonar-eventcard_categorys").innerHTML += '<div className="detail-sonar-eventcard_category" style="max-width: 50%; height: auto; border-radius: 1vh; display: flex; justify-content: center; position: relative; left: 0; background: orange;"><label style="font-size: 1.9vh; margin: 0.27vh 0.65vw 0.27vh 0.65vw; font-weight: bolder; color: black; overflow: hidden;">' + category.name + '</label></div>'}
                    );


                    data.startDate = format_date_to_text(data.startDate);
                    setEvent(data);
                    getGeocodeEvent(data).then(position => {
                        const iframelink = `https://www.openstreetmap.org/export/embed.html?bbox=${position.lon-0.01004219055176}%2C${position.lat-0.00432886683549}%2C${position.lon + 0.01004219055176}%2C${position.lat + 0.00432886683549}&amp;layer=mapnik&amp;marker=${position.lat}%2C${position.lon}`;
                        const linktomaps = `https://www.google.com/maps/dir/?api=1&destination=${position.street}+${position.houseNumber},+${position.postcode}+${position.city}`.replaceAll(" ", "+");
                        document.getElementById("detail-sonar-eventcard_content_text-button").innerHTML = `<a href=${linktomaps}>Route auf Google Maps</a>`;
                        document.getElementById("detail-sonar-eventcard_map-container").innerHTML = `<iframe src=${iframelink}>`;
                    });
                })
                .then(() => (bookmarked = localStorage.getItem("bookmarks")?.includes(parseInt(eventId))))
                .catch(err => {
                    console.log("Event mit ID ", eventId, " konnte nicht geladen werden:" +
                        " " + err.message);
                    goBack();
                });
        }
    }, [eventId]);

    const handleBookmarking = () => {
        let loggedIn = (localStorage.getItem("email") !== null);
        if (!loggedIn) {
            navigate("/profile")
        } else {
            let bookmarkedEvents = JSON.parse(localStorage.getItem("bookmarks"));
            console.log(bookmarkedEvents);
            if (bookmarkedEvents === null) bookmarkedEvents = []
            // Event zu Favoriten hinzufügen
            if (!bookmarkedEvents.includes(parseInt(eventId))) {
                bookmarked = true;
                bookmarkedEvents.push(parseInt(eventId));
                document.getElementById(parseInt(eventId)).className = "detail-sonar-eventcard_top-icon bookmarked"
                localStorage.setItem("bookmarks", JSON.stringify(bookmarkedEvents));
                const link = "http://localhost:8081/api/v1/user/favourites/add?email=" + localStorage.getItem("email") + "&eventId=" + parseInt(eventId);
                fetch(link, {method: "POST"})
                    .catch(err => console.log("Event konnte nicht gespeichert werden:" +
                        " " + err.message));
            } else {
                bookmarked = false;
                document.getElementById(parseInt(eventId)).className = "detail-sonar-eventcard_top-icon"
                const newBookmarks = bookmarkedEvents.filter(ev => ev !== parseInt(eventId));
                localStorage.setItem("bookmarks", JSON.stringify(newBookmarks));
                console.log(localStorage.getItem("bookmarks"));
                const link = "http://localhost:8081/api/v1/user/favourites/remove?email=" + localStorage.getItem("email") + "&eventId=" + parseInt(eventId);
                fetch(link, {method: "POST"})
                    .catch(err => console.log("Event konnte nicht aus den Favoriten entfernt werden:" +
                        " " + err.message));
            }
        }
        console.log(bookmarked);
    }

    function renderBookmarkedIcons() {
        if (bookmarked) {
            document.getElementById(parseInt(eventId)).className = "detail-sonar-eventcard_top-icon bookmarked";
        }
    }

    function goBack() {
        history.back();
    }


    function formatAddress() {
        if (event.address !== undefined) {
            return <>
                {event.address.street} {event.address.houseNumber}<br/>
                {event.address.postcode} {event.address.city} {event.address.district}<br/>
            </>
        }
    }


    async function getGeocodeEvent(event){
        const {street, houseNumber, postcode, city} = event.address;
        const query = `${street} ${houseNumber} ${postcode} ${city}`;
        const url = `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(query)}`;
        try {
            const res = await fetch(url, {
                headers: {'User-Agent': 'sonar'},
            });
            const data = await res.json();
            if (data.length > 0) {
                const lat = parseFloat(data[0].lat);
                const lon = parseFloat(data[0].lon);
                return {lat, lon, street, houseNumber, postcode, city};
            }
        } catch (error) {
            console.error("Geocoding failed:", error);
        }
    }

    //SHARE
    const [snackbarMessage, setSnackbarMessage] = useState("Link kopiert!");
    const [snackbarSeverity, setSnackbarSeverity] = useState("success");
    const [snackbarOpen, setSnackbarOpen] = useState(false);

    const handleCopy = async () => {
        try {
            await navigator.clipboard.writeText(`${window.location.origin}/event?eventId=${encodeURIComponent(eventId)}`);
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

    return (
        <Card className="detail-sonar-eventcard" onLoad={renderBookmarkedIcons}>
            <div className="detail-sonar-eventcard_container">
                <div className="detail-sonar-eventcard_media-div">
                    <CardMedia className="detail-sonar-eventcard_media"
                               component="img"
                               alt="family"
                               src={`http://localhost:8081/images/${event.image ? event.image : 'placeholder.png'}`}
                    />
                    <div className="detail-sonar-eventcard_header">
                        <CardActions className="detail-sonar-eventcard_top-icons">
                            <div className="detail-sonar-eventcard_top-icon">
                                <Button size="small" onClick={handleCopy}><ShareIcon/></Button>
                            </div>
                            <div className="detail-sonar-eventcard_top-icon" id={eventId}>
                                <Button size="small" onClick={handleBookmarking}><BookmarkIcon/></Button>
                            </div>
                            <div className="detail-sonar-eventcard_top-icon-buffer"></div>
                            <div className="detail-sonar-eventcard_top-cancel">
                                <Button onClick={goBack}><CancelIcon/></Button>
                            </div>
                        </CardActions>
                        <CardActions className="detail-sonar-eventcard_tags">
                            <div className="detail-sonar-eventcard_tag-icon detail-sonar-eventcard_tag-icon-date">
                                <label id="label-date"/>
                            </div>
                            <div className="detail-sonar-eventcard_tag-icon detail-sonar-eventcard_tag-icon-registration">
                                <label id="label-reg"/>
                            </div>
                        </CardActions>
                    </div>
                </div>

                <div className="detail-sonar-eventcard_content">

                    <CardContent id="detail-sonar-eventcard_content_text-div">

                        <div className="detail-sonar-eventcard_content_text">
                            <p className="detail-sonar-eventcard_content_text-date">
                                Datum: {event.startDate}
                            </p>
                            <p className="detail-sonar-eventcard_content_text-costs">
                                Preis: {event.price}€
                            </p>
                            <p className="detail-sonar-eventcard_content_text-title">
                                {event.name}
                            </p>
                            <p className="detail-sonar-eventcard_content_text-headline">
                                {event.headline}
                            </p>
                            <p className="detail-sonar-eventcard_content_text-text">
                                {event.description}
                            </p>
                            <CardActions id="detail-sonar-eventcard_categorys"/>
                            <div id="detail-sonar-eventcard_map-container"/>
                            <p className="detail-sonar-eventcard_content_text-location">
                                Adresse<br/>{formatAddress()}<br/>
                            </p>
                            <p className="detail-sonar-eventcard_content_text-location">
                                Organisator<br/>{event.applicant !== undefined && event.applicant.organisation}
                            </p>
                            <div id="detail-sonar-eventcard_content_text-button"/>
                        </div>
                    </CardContent>
                </div>
            </div>
            <Snackbar
                open={snackbarOpen}
                autoHideDuration={3000}
                onClose={handleSnackbarClose}
                anchorOrigin={{ vertical: 'bottom', horizontal: 'left' }}
            >
                <MuiAlert onClose={handleSnackbarClose} severity={snackbarSeverity} sx={{ width: '100%' }}>
                    {snackbarMessage}
                </MuiAlert>
            </Snackbar>
        </Card>
    )
}