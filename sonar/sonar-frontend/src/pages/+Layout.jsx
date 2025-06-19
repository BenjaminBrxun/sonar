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
    const isSearchPage = pageContext.urlPathname.startsWith("/search");
    const isdetailPage = pageContext.urlPathname.startsWith("/event");
    return <>
        {!isFilterPage && !isSearchPage && !isdetailPage && <InterfaceHeader/>}
        <div className="current-component">
            {children}
        </div>
        {<InteractiveMap/>}
        {!isFilterPage && !isSearchPage && !isdetailPage && <InterfaceFooter/>}
    </>
}
