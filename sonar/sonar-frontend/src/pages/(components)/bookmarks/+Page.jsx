import "./+Page.scss"
import {FavoritesComponent} from "../../../components/overlays/bookmark/FavoritesComponent.jsx";
import {ListComponent} from "../../../components/overlays/listing/ListComponent.jsx";
import React from "react";

export default function () {
    let link = "";

    return <>
        <ListComponent link={link} isFav={true}/>
    </>;
}