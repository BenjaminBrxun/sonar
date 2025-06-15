import React from 'react'
import './App.scss'
import {InterfaceHeader} from './components/interface/interfaceHeader/InterfaceHeader.jsx'
import {InterfaceFooter} from './components/interface/interfaceFooter/InterfaceFooter.jsx'
import {InteractiveMap} from "./components/interactivemap/InteractiveMap.jsx";
import EventCardModule from "./components/modules/EventCardModule/EventCardModule.jsx";

//import {SonarEvent} from "./model/sonarevent/SonarEvent.jsx";
import eventCardImageGaming from './assets/images/event-mocks/gaming.png';  // die Bilder werden noch
import eventCardImageFamily from './assets/images/event-mocks/family.png';  // über eine Schnittstelle
import eventCardImagePark from './assets/images/event-mocks/park.png';      // dynamisch aus dem Backend gezogen.
import eventCardImageParty from './assets/images/event-mocks/party.png';    // noch nur Platzhalter

function App() {

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
            <InterfaceFooter/>
        </div>

    )
}


export default App
