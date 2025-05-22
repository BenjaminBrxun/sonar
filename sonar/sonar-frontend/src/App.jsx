import React from 'react'
import './App.scss'
import {InterfaceHeader} from './components/interface/interfaceHeader/InterfaceHeader.jsx'
import {InterfaceFooter} from './components/interface/interfaceFooter/InterfaceFooter.jsx'
import {InteractiveMap} from "./components/interactivemap/InteractiveMap.jsx";
//import {SonarEvent} from "./model/sonarevent/SonarEvent.jsx";


function App() {

    return (
        <div className="sonar-body">
            <InterfaceHeader/>
            <div className="card-container">

            </div>
            <InteractiveMap/>
            <InterfaceFooter/>
        </div>

    )
}


export default App
