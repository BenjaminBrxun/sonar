import React from 'react';
import {InteractiveMap} from '../../../components/interactivemap/InteractiveMap.jsx';
import {usePageContext} from "vike-react/usePageContext";



export default function Test() {
    const {urlParsed} = usePageContext();
    const linkFromQuery = urlParsed.search?.link;
    const link = linkFromQuery || "http://localhost:8081/api/v1/events"
    console.log("list +Page aufgerufen");
    return <>
        <InteractiveMap link={link}/>
    </>;
}