import "./+Page.scss";
import React from "react";
import {ListComponent} from "../../../components/overlays/listing/ListComponent.jsx";
import {usePageContext} from "vike-react/usePageContext";

export default function Test() {
    const {urlParsed} = usePageContext();
    const linkFromQuery = urlParsed.search?.link;
    const link = linkFromQuery || "http://localhost:8081/api/v1/events"
    return (<>
            <ListComponent link={link}/>
        </>
    );
}