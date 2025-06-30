import { useEffect, useState } from "react";

export const InteractiveMap = () => {
    const [MapImpl, setMapImpl] = useState(null);

    useEffect(() => {
        import("./LeafletMap.jsx").then((mod) => {
            setMapImpl(() => mod.LeafletMap);
        });
    }, []);

    if (!MapImpl) return null;

    return <MapImpl />;
};
