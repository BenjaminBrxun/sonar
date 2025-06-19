import {InterfaceHeader} from "../../../components/interface/interfaceHeader/InterfaceHeader.jsx";
import EventCardModule from "../../../components/modules/EventCardModule/EventCardModule.jsx";
import eventCardImageGaming from "../../../assets/images/event-mocks/gaming.png";
import eventCardImageParty from "../../../assets/images/event-mocks/party.png";
import eventCardImageFamily from "../../../assets/images/event-mocks/family.png";
import eventCardImagePark from "../../../assets/images/event-mocks/park.png";
import {InteractiveMap} from "../../../components/interactivemap/InteractiveMap.jsx";
import {InterfaceFooter} from "../../../components/interface/interfaceFooter/InterfaceFooter.jsx";
import "./+Page.scss";
import React, {useState, useEffect} from "react";
import {ListComponent} from "../../../components/overlays/listing/ListComponent.jsx";
import {usePageContext} from "vike-react/usePageContext";

export default function Test() {
    const {urlParsed} = usePageContext();
    const linkFromQuery = urlParsed.search?.link;
    const link = linkFromQuery || "http://localhost:8081/api/v1/events/all"
    console.log("list +Page aufgerufen");
    return (<>
            <ListComponent link={link} />
        </>
    );
}