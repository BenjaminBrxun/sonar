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
    const isMapPage = pageContext.urlPathname.startsWith("/interactivemap");
    const isLoginPage = pageContext.urlPathname.startsWith("/profile");

    return (
        <div className="layout-wrapper">
            {!isMapPage && <Background  />}
            <div className="ui">
                {!isFilterPage && !isLoginPage && <InterfaceHeader />}
                <div className="current-component">{children}</div>
                {!isFilterPage && !isLoginPage && <InterfaceFooter currentPage={pageContext.urlPathname}/>}
            </div>
        </div>
    );

}