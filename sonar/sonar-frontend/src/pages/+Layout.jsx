import {InterfaceHeader} from "../components/interface/interfaceHeader/InterfaceHeader.jsx";
import {InterfaceFooter} from "../components/interface/interfaceFooter/InterfaceFooter.jsx";
import {Background} from "../components/background/Background.jsx";
import React from "react";
import {usePageContext} from "vike-react/usePageContext";
import "./Layout.scss";


export {Layout}

function Layout({ children }) {
    const pageContext = usePageContext();
    const isFilterPage = pageContext.urlPathname.startsWith("/filter");
    const isSearchPage = pageContext.urlPathname.startsWith("/search");
    const isMapPage = pageContext.urlPathname.startsWith("/interactivemap");

    return (
        <div className="layout-wrapper">
            {!isMapPage && <Background  />}
            <div className="ui">
                {!isFilterPage && !isSearchPage && <InterfaceHeader />}
                <div className="current-component">{children}</div>
                {!isFilterPage && !isSearchPage && <InterfaceFooter />}
            </div>
        </div>
    );
}