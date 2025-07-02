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
                {events.map((event) => (<EventCardModule costs={event.price} date={event.startDate} date_text={format_date_to_text(event.startDate)} image={eventCardImagePark} restricted={event.restricted} title={event.name} key={event.id}/>)
                )}
                <div className="sonar-content-buffer-end"></div>
            </div>
        </div>
    </>;

}