import React from "react";
import { MapContainer, TileLayer } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import "./InteractiveMap.scss";

export const LeafletMap = () => {
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
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
            </MapContainer>
        </div>
    );
};
