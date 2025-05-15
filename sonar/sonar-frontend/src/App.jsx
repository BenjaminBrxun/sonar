import React, {useState} from 'react'
import './App.scss'
import {InterfaceHeader} from './components/interface/interfaceHeader/InterfaceHeader.jsx'
import {InterfaceFooter} from './components/interface/interfaceFooter/InterfaceFooter.jsx'
import {InteractiveMap} from "./components/interactivemap/InteractiveMap.jsx";


function App() {
    const [page, setPage] = useState("map")

    const [dataFromChild, setDataFromChild] = useState(false);

    function handleDataFromChild(data) {
        setDataFromChild(data);
        setPage("map");
        setDataFromChild(false);
    }

    function _toggle(newPage) {
        if (page === newPage || dataFromChild) {
            setPage("map");
            setDataFromChild(false);
        } else {
            setPage(newPage)
        }
    }

    return (
        <div className="sonar-body" style={{width:'100vw',height:'100vh'}}>
            <InterfaceHeader/>
            <InteractiveMap/>
            <InterfaceFooter/>
        </div>

    )
}


export default App
