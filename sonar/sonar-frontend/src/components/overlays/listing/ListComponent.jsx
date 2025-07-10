import React, {useEffect, useState} from "react";
import "./ListComponent.scss"
import EventCardModule from "../../modules/EventCardModule/EventCardModule.jsx";
import eventCardImagePark from "../../../assets/images/event-mocks/park.png";
import {format_date_to_text} from "../../modules/TagsAndTime/TagsAndTime.js";


export function ListComponent({link}) {
    const [loadedLink] = useState(null);
    const [events, setEvents] = useState([]);
    useEffect(() => {
        if (link === loadedLink) return;
        fetch(link)
            .then(res => res.json())
            .then(data => setEvents(data))
            .catch(err => console.log("Event konnte nicht geladen werden:" +
                " " + err.message));
    }, []);


    return <>
        <div className="sonar-body">

            <div className="sonar-content card-container">
                <div className="sonar-content-buffer-begin"></div>
                {events.map((event) => (
                    <EventCardModule costs={event.price} date={event.startDate} date_text={format_date_to_text(event.startDate)} image={eventCardImagePark}
                                     restricted={event.restricted} title={event.name} id={event.id} key={event.id}/>)
                )}
                <div className="sonar-content-buffer-end"></div>
            </div>
        </div>
    </>;

}