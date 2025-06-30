import { useEffect, useState } from "react";

export const InteractiveMap = ({ link }) => {
    const [LeafletMap, setLeafletMap] = useState(null);
    const [events, setEvents] = useState([]);
    console.log(link);

    useEffect(() => {
        import("./LeafletMap.jsx").then((mod) => {
            setLeafletMap(() => mod.LeafletMap);
        });
    }, []);

    useEffect(() => {
        if (!link) return;
        fetch(link)
            .then((res) => res.json())
            .then((data) => setEvents(data))
            .catch((err) => console.error("Fehler beim Laden der Events:", err));
    }, [link]);

    if (!LeafletMap) return null;

    return <LeafletMap events={events}/>;
};
