import React, { useEffect, useState } from "react";
import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import "./InteractiveMap.scss";
import {navigate} from "vike/client/router";

import iconUrl from 'leaflet/dist/images/marker-icon.png';
import iconRetinaUrl from 'leaflet/dist/images/marker-icon-2x.png';
import shadowUrl from 'leaflet/dist/images/marker-shadow.png';

// Leaflet Icon Setup
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

        setEventMarkers([]); // leeren beim neuen Laden

        const geocodeEvent = async (events) => {
            console.log("Lade Koordinaten für Events...", events);
            for (const event of events) {
                const { street, houseNumber, postcode, city } = event.address;
                const query = `${street} ${houseNumber} ${postcode} ${city}`;
                const url = `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(query)}`;

                try {
                    const res = await fetch(url, {
                        headers: { 'User-Agent': 'sonar' },
                    });
                    const data = await res.json();
                    if (data.length > 0) {
                        const lat = parseFloat(data[0].lat);
                        const lon = parseFloat(data[0].lon);
                        const marker = {
                            id: event.id,
                            name: event.name,
                            position: [lat, lon],
                        };
                        setEventMarkers(prev => {
                            if (prev.some(m => m.id === marker.id)) return prev;
                            return [...prev, marker];
                        });

                    }
                } catch (error) {
                    console.error("Geocoding failed:", error);
                }
            }

        };

        geocodeEvent(events).finally(() => console.log("Alle Koordinaten geladen."));

    }, [events]);


    return (
        <div className="interactiveMap-container">
            <MapContainer
                center={[51.5380, 7.2257]} //Herne
                zoom={13}
                scrollWheelZoom
                style={{ height: "100%", width: "100%" }}
            >
                <TileLayer
                    attribution='&copy; OpenStreetMap'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
                {eventMarkers.map(event => (
                    <Marker key={event.id} position={event.position}>
                        <Popup>
                            <div>
                                <strong>{event.name}</strong><br />
                                <button
                                    //onClick={() => navigate(`/events/${event.id}`)}
                                    onClick={() => alert(`Event: ${event.name} (ID: ${event.id})`)}
                                    style={{ marginTop: "5px", cursor: "pointer" }}
                                >
                                    Zur Detailseite
                                </button>
                            </div>
                        </Popup>
                    </Marker>
                ))}
            </MapContainer>
        </div>
    );
};
