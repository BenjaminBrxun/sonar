import React from 'react';
import "./+Page.scss";
import {InterfaceFooter} from "../../components/interface/interfaceFooter/InterfaceFooter.jsx";
import {InterfaceHeader} from "../../components/interface/interfaceHeader/InterfaceHeader.jsx";
import {InteractiveMap} from "../../components/interactivemap/InteractiveMap.jsx";
import {navigate} from "vike/client/router";
import EventCardModule from "../../components/modules/EventCardModule/EventCardModule.jsx";
import eventCardImageGaming from "../../assets/images/event-mocks/gaming.png";
import eventCardImageParty from "../../assets/images/event-mocks/party.png";
import eventCardImageFamily from "../../assets/images/event-mocks/family.png";
import eventCardImagePark from "../../assets/images/event-mocks/park.png";

export { Page };

async function goToFilter() {
    const navigationPromise = navigate('/filter')
    console.log("The URL changed but the new page hasn't rendered yet.")
    await navigationPromise
    console.log('The new page has finished rendering.')
}

function Page() {
    return (
    <div className="sonar-body">
        <InterfaceHeader/>
        <div className="sonar-content card-container">
            <div className="sonar-content-buffer-begin"></div>
            <EventCardModule costs={"Kostenlos (ab 6 Jahren)"} date={"07.06.2025, ab 18:00 Uhr"} image={eventCardImageGaming} title={"Gaming Night"}/>
            <EventCardModule costs={"5€ (ab 14 Jahren)"} date={"09.06.2025, ab 19:30 Uhr"} image={eventCardImageParty} title={"U20-Party"}/>
            <EventCardModule costs={"Kostenlos"} date={"08.07.2025, ab 14:00 Uhr"} image={eventCardImageFamily} title={"Familienfest"}/>
            <EventCardModule costs={"5€ (Kinder unter 6 kostenlos"} date={"26.07.2025, ab 12:00 Uhr"} image={eventCardImagePark} title={"Grillen im Park"}/>
            <div className="sonar-content-buffer-end"></div>
        </div>
        <InteractiveMap/>
        <InterfaceFooter onFilterClick={goToFilter}></InterfaceFooter>
    </div>
    )
}
