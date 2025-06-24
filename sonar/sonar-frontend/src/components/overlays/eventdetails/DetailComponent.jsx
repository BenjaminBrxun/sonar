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
import image from "../../../assets/images/event-mocks/gaming.png"
//import map from "../../../assets/images/map_dark.png"

const linktomaps = "https://www.google.com/maps/place/Herne/@51.5382671,7.1688761,10911m/data=!3m2!1e3!4b1!4m6!3m5!1s0x47b8e1836478a315:0x427f28131548780!8m2!3d51.5368948!4d7.2009147!16zL20vMDE4aG5z?authuser=0&entry=ttu&g_ep=EgoyMDI1MDYxNy4wIKXMDSoASAFQAw%3D%3D"


export function DetailComponent({eventId}) { //eigentlich {title, date, costs, image} hier als Argument

    const [event, setEvent] = useState([]);

    useEffect(() => {
        fetch(`http://localhost:8081/api/v1/event/${eventId}`)
            .then(res => res.json())
            .then(data => {
                setEvent(data);
            })
            .then(() => console.log("event erhalten: ", event))
            .catch(err => console.log("Event mit ID ", eventId, " konnte nicht geladen werden:" +
                " " + err.message));
    }, [event, eventId]);

    function formatAddress() {
        if(event.address !== undefined) {
            return <div>
                <p>{event.address.street} {event.address.houseNumber}</p>
                <p>{event.address.postcode} {event.address.city} {event.address.district}</p>
            </div>
        }
    }

    return (
        <Card className="sonar-eventcard">
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
                                <Button size="small"><ShareIcon/></Button>
                            </div>
                            <div className="sonar-eventcard_top-icon">
                                <Button size="small"><BookmarkIcon/></Button>
                            </div>
                            <div className="sonar-eventcard_top-icon-buffer"></div>
                            <div className="sonar-eventcard_top-cancel">
                                <Button><CancelIcon/></Button>
                            </div>
                        </CardActions>
                        <CardActions className="sonar-eventcard_tags">
                            <div className="sonar-eventcard_tag-icon sonar-eventcard_tag-icon-date">
                                <label>Heudde</label>
                            </div>
                            <div className="sonar-eventcard_tag-icon sonar-eventcard_tag-icon-registration">
                                <label>Ausjebuucht</label>
                            </div>
                        </CardActions>
                    </div>
                </div>

                <div className="sonar-eventcard_content">

                    <CardContent id="sonar-eventcard_content_text-div">

                        <div className="sonar-eventcard_content_text">
                            <p className="sonar-eventcard_content_text-date">
                                Datum: {event.startDate}
                            </p>
                            <p className="sonar-eventcard_content_text-costs">
                                Preis: {event.price}
                            </p>
                            <p className="sonar-eventcard_content_text-title">
                                {event.name}
                            </p>
                            <p className="sonar-eventcard_content_text-headline">
                                {event.headline}
                            </p>
                            <p className="sonar-eventcard_content_text-text">
                                {event.description}
                            </p>
                            <CardActions className="sonar-eventcard_categorys">
                                <div className="sonar-eventcard_category">
                                    <label>Gaming</label>
                                </div>
                                <div className="sonar-eventcard_category">
                                    <label>Computer</label>
                                </div>
                            </CardActions>
                            <iframe className="sonar-eventcard_map" src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2481.326769624289!2d7.233790842804277!3d51.543906835387396!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x47b8e1f60f2bd079%3A0xced4b1949a1d5e26!2sHerner%20Stadtgarten%2C%20Herne!5e0!3m2!1sde!2sde!4v1750675117560!5m2!1sde!2sde"></iframe>
                            <p className="sonar-eventcard_content_text-location">
                                Adresse<br/>{formatAddress()}<br/>
                            </p>
                            <p className="sonar-eventcard_content_text-location">
                                Organisator<br/>{event.applicant !== undefined && event.applicant.organisation}
                            </p>
                            <div className="sonar-eventcard_content_text-button">
                                <a href={linktomaps}>Route auf Google Maps</a>
                            </div>

                        </div>
                    </CardContent>
                </div>
            </div>
        </Card>
    )
}