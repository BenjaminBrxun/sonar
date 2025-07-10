import React, {useEffect, useState} from "react";
import "./ListComponent.scss"
import EventCardModule from "../../modules/EventCardModule/EventCardModule.jsx";
import eventCardImagePark from "../../../assets/images/event-mocks/park.png";

function format_date_to_text(eventdate) {
    const year = eventdate.substring(0, 4);
    const month = eventdate.substring(5, 7);
    const day = eventdate.substring(8, 10);
    const hour = eventdate.substring(11, 13);
    const minute = eventdate.substring(14, 16);
    // const second = eventdate.substring(17, 19);
    const erg = day + "." + month + "." + year + " " + hour + ":" + minute + "Uhr";
    console.log("LOGGGG>>"+erg)
    return erg;
}


export function ListComponent({link}) {
    const [loadedLink] = useState(null);
    const [events, setEvents] = useState([]);
    useEffect(() => {
        if (link === loadedLink) return;
        fetch(link)
            .then(res => res.json())
            .then(data => setEvents(data))
            .then(data => console.log(data))
            .catch(err => console.log("Event konnte nicht geladen werden:" +
                " " + err.message));
    }, []);


    return <div className="sonar-body">
            <div className="sonar-content card-container">
                <div className="sonar-content-buffer-begin"></div>
                {events.map((event) => (
                    <EventCardModule costs={event.price} date={event.startDate} date_text={format_date_to_text(event.startDate)} image={eventCardImagePark}
                                     restricted={event.restricted} title={event.name} id={event.id} key={event.id}/>)
                )}
                <div className="sonar-content-buffer-end"></div>
            </div>
        </div>
}