import {DetailComponent} from "../../../components/overlays/eventdetails/DetailComponent.jsx";
import {usePageContext} from "vike-react/usePageContext";

export default function Test() {
    const {urlParsed} = usePageContext();
    const linkFromQuery = urlParsed.search?.link;
    const link = linkFromQuery

    return <>
        <DetailComponent eventId={link}/>
    </>
}