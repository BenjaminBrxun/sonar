import React from 'react'
import './App.scss'
import {InterfaceHeader} from './components/interface/interfaceHeader/InterfaceHeader.jsx'
import {InterfaceFooter} from './components/interface/interfaceFooter/InterfaceFooter.jsx'
import {InteractiveMap} from "./components/interactivemap/InteractiveMap.jsx";
import EventCardModule from "./components/modules/EventCardModule/EventCardModule.jsx";
//import {SonarEvent} from "./model/sonarevent/SonarEvent.jsx";


function App() {

    return (
        <div className="sonar-body">
            <InterfaceHeader/>
            <div className="sonar-content card-container">
                <div className="sonar-content-buffer-begin"></div>
                <EventCardModule/>
                <EventCardModule/>
                <EventCardModule/>
                <EventCardModule/>
                <div className="sonar-content-buffer-end"></div>
            </div>
            <InteractiveMap/>
            <InterfaceFooter/>
        </div>

    )
}


export default App
