import {InterfaceHeader} from "../components/interface/interfaceHeader/InterfaceHeader.jsx";
import {InteractiveMap} from "../components/interactivemap/InteractiveMap.jsx";
import {InterfaceFooter} from "../components/interface/interfaceFooter/InterfaceFooter.jsx";
import React from "react";
import {usePageContext} from "vike-react/usePageContext";
import "./Layout.scss";


export {Layout}

function Layout({children}) {
    const pageContext = usePageContext();
    const isFilterPage = pageContext.urlPathname.startsWith("/filter");
    return <>
        {!isFilterPage && <InterfaceHeader/>}
        <div className="current-component">
            {children}
        </div>
        {<InteractiveMap/>}
        {!isFilterPage && <InterfaceFooter currentPage={pageContext.urlPathname}/>}
    </>
}
