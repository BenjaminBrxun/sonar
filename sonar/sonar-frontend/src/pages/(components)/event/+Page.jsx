import {DetailComponent} from "../../../components/overlays/eventdetails/DetailComponent.jsx";
import {usePageContext} from "vike-react/usePageContext";

export default function Test() {
    const {urlParsed} = usePageContext();
    const eventId = urlParsed.search?.eventId;

    return <>
        <DetailComponent eventId={eventId}/>
    </>
}