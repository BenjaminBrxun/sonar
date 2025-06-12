import {InterfaceHeader} from "../components/interface/interfaceHeader/InterfaceHeader.jsx";
import {InteractiveMap} from "../components/interactivemap/InteractiveMap.jsx";
import {InterfaceFooter} from "../components/interface/interfaceFooter/InterfaceFooter.jsx";
import React from "react";

export {Layout}

function Layout({children}) {
    return <>
        <InterfaceHeader></InterfaceHeader>
        <div className="current-component">
            {children}
        </div>
        <InteractiveMap/>
        <InterfaceFooter></InterfaceFooter>
    </>
}
