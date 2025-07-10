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
import {costsWithCurrency,dateToDateSpan, restrictedToString, format_date_to_text} from "../../modules/TagsAndTime/TagsAndTime.js"

export function DetailComponent({eventId}) {

    const [event, setEvent] = useState([]);
    useEffect(() => {
        if (eventId !== null && eventId !== undefined) {
            fetch(`http://localhost:8081/api/v1/event/${eventId}`)
                .then(res => res.json())
                .then(data => {
                    setLabels(dateToDateSpan(data.startDate)[0], restrictedToString(data.restricted));

                    data.startDate = format_date_to_text(data.startDate);
                    setEvent(data);
                    getGeocodeEvent(data).then(position => {
                        const iframelink = `https://www.openstreetmap.org/export/embed.html?bbox=${position.lon-0.01004219055176}%2C${position.lat-0.00432886683549}%2C${position.lon + 0.01004219055176}%2C${position.lat + 0.00432886683549}&amp;layer=mapnik&amp;marker=${position.lat}%2C${position.lon}`;
                        const linktomaps = `https://www.google.com/maps/dir/?api=1&destination=${position.street}+${position.houseNumber},+${position.postcode}+${position.city}`.replaceAll(" ", "+");
                        document.getElementById("sonar-eventcard_content_text-button").innerHTML = `<a href=${linktomaps}>Route auf Google Maps</a>`;
                        document.getElementById("sonar-eventcard_map-container").innerHTML = `<iframe src=${iframelink}>`;
                    });
                })
                .catch(err => {
                    console.log("Event mit ID ", eventId, " konnte nicht geladen werden:" +
                        " " + err.message);
                    goBack();
                });
        }
    }, [eventId]);
    
    function setLabels(date, reg){
        document.getElementById("DateLabel").innerHTML = date;
        document.getElementById("RegistrationLabel").innerHTML = reg;
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

    return (
        <Card className="detail-sonar-eventcard">
            <div className="detail-sonar-eventcard_container">
                <div className="detail-sonar-eventcard_media-div">
                    <CardMedia className="detail-sonar-eventcard_media"
                               component="img"
                               alt="family"
                               image={image}
                    />
                    <div className="detail-sonar-eventcard_header">
                        <CardActions className="detail-sonar-eventcard_top-icons">
                            <div className="detail-sonar-eventcard_top-icon">
                                <Button size="small"><ShareIcon/></Button>
                            </div>
                            <div className="detail-sonar-eventcard_top-icon">
                                <Button size="small"><BookmarkIcon/></Button>
                            </div>
                            <div className="detail-sonar-eventcard_top-icon-buffer"></div>
                            <div className="detail-sonar-eventcard_top-cancel">
                                <Button onClick={goBack}><CancelIcon/></Button>
                            </div>
                        </CardActions>
                        <CardActions className="detail-sonar-eventcard_tags">
                            <div className="detail-sonar-eventcard_tag-icon sonar-eventcard_tag-icon-date">
                                <label id="DateLabel"></label>
                            </div>
                            <div className="detail-sonar-eventcard_tag-icon sonar-eventcard_tag-icon-registration">
                                <label id="RegistrationLabel"></label>
                            </div>
                        </CardActions>
                    </div>
                </div>

                <div className="detail-sonar-eventcard_content">

                    <CardContent id="sonar-eventcard_content_text-div">

                        <div className="detail-sonar-eventcard_content_text">
                            <p className="detail-sonar-eventcard_content_text-date">
                                Datum: {event.startDate}
                            </p>
                            <p className="detail-sonar-eventcard_content_text-costs">
                                Preis: {costsWithCurrency(event.price)}
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
                            <CardActions className="detail-sonar-eventcard_categorys">
                                <div className="detail-sonar-eventcard_category">
                                    <label>Gaming</label>
                                </div>
                                <div className="detail-sonar-eventcard_category">
                                    <label>Computer</label>
                                </div>
                            </CardActions>
                            <div id="sonar-eventcard_map-container"/>
                            <p className="detail-sonar-eventcard_content_text-location">
                                Adresse<br/>{formatAddress()}<br/>
                            </p>
                            <p className="detail-sonar-eventcard_content_text-location">
                                Organisator<br/>{event.applicant !== undefined && event.applicant.organisation}
                            </p>
                            <div id="sonar-eventcard_content_text-button"/>
                        </div>
                    </CardContent>
                </div>
            </div>
        </Card>
    )
}