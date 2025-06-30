import React, { useEffect, useState } from "react";
import { MapContainer, TileLayer, Marker, Popup} from "react-leaflet";
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import "./InteractiveMap.scss";

import iconUrl from 'leaflet/dist/images/marker-icon.png';
import iconRetinaUrl from 'leaflet/dist/images/marker-icon-2x.png';
import shadowUrl from 'leaflet/dist/images/marker-shadow.png';


delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
    iconRetinaUrl,
    iconUrl,
    shadowUrl,
});


export const LeafletMap = ({ events }) => {
    const [eventMarkers, setEventMarkers] = useState([]);

    useEffect(() => {
        if (!events || events.length === 0) return;

        const geocodeEvent = async (event) => {
            let street = event.address.street;
            let houseNumber = event.address.houseNumber;
            let postCode = event.address.postcode;
            let city = event.address.city;
            console.log(street + " " + houseNumber);
            const url = `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(street + " " + houseNumber + " " + postCode + " " + city)}`;
            const res = await fetch(url, {
                headers: { 'User-Agent': 'deine-app-name' }
            });
            const data = await res.json();
            if (data.length > 0) {
                console.log("position: ", parseFloat(data[0].lat), parseFloat(data[0].lon))
                let marker = {
                    id: event.id,
                    name: event.name,
                    position: [parseFloat(data[0].lat), parseFloat(data[0].lon)],
                };
                setEventMarkers(marker);
            }
            return null;
        };

        const loadMarkers = async () => {
            const markers = await Promise.all(events.map(geocodeEvent));
            setEventMarkers(markers);
        };

        loadMarkers();
    }, []);


    return (
        <div className="interactiveMap-container">
            <MapContainer
                center={[51.5380, 7.2257]} // Herne
                zoom={13}
                zoomControl={false}
                scrollWheelZoom={true}
                touchZoom={true}
                dragging={true}
                style={{ height: "100%", width: "100%" }}
            >
                <TileLayer
                    attribution='&copy; OpenStreetMap'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
                {eventMarkers.map(event => (
                    <Marker key={event.id} position={event.position}>
                        <Popup>{event.name}</Popup>
                    </Marker>
                ))}
            </MapContainer>
        </div>
    );
};
