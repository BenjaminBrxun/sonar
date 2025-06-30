import React, {useEffect, useState} from "react";
import "./ListComponent.scss"
import {BaseComponent} from "../../base/BaseComponent.jsx";
import EventCardModule from "../../modules/EventCardModule/EventCardModule.jsx";
import eventCardImagePark from "../../../assets/images/event-mocks/park.png";


export function ListComponent({link}) {
    const [loadedLink, setLoadedLink] = useState(null);
    const [events, setEvents] = useState([]);
    console.log(link);
    useEffect(() => {
        if(link === loadedLink) return;
        fetch(link)
            .then(res => res.json())
            .then(data => setEvents(data))
            .catch(err => console.log("Event konnte nicht geladen werden:" +
                " " + err.message));
    }, [link]);


    return <>
        <div className="sonar-body">

            <div className="sonar-content card-container">
                <div className="sonar-content-buffer-begin"></div>
                {/*<EventCardModule costs={"Kostenlos (ab 6 Jahren)"} date={"07.06.2025, ab 18:00 Uhr"} image={eventCardImageGaming} title={"Gaming Night"}/>*/}
                {/*<EventCardModule costs={"5€ (ab 14 Jahren)"} date={"09.06.2025, ab 19:30 Uhr"} image={eventCardImageParty} title={"U20-Party"}/>*/}
                {/*<EventCardModule costs={"Kostenlos"} date={"08.07.2025, ab 14:00 Uhr"} image={eventCardImageFamily} title={"Familienfest"}/>*/}
                {/*<EventCardModule costs={"5€ (Kinder unter 6 kostenlos"} date={"26.07.2025, ab 12:00 Uhr"} image={eventCardImagePark} title={"Grillen im Park"}/>*/}
                {events.map((event) => (<EventCardModule costs={event.price} date={event.startDate} image={eventCardImagePark} restricted={event.restricted} title={event.name} key={event.id}/>)
                )}
                <div className="sonar-content-buffer-end"></div>
            </div>
        </div>
    </>;

}