import {FeatureGroup, Marker, Popup, useMap} from "react-leaflet";
import {LatLng} from "leaflet";
import {useRef} from "react";
import Location from "../types/Location";

export default function LocationMarkerGroup({locations}: {
    locations: Location[],
}) {
    const map = useMap();
    const markerGroupRef = useRef<L.FeatureGroup>(null);

    const showAllMarkersOnMap = () => {
        if (!map || !markerGroupRef.current) {
            return;
        }
        map.invalidateSize();
        map.flyToBounds(markerGroupRef.current.getBounds(), {padding: [3, 3]});
    }

    return (
        <FeatureGroup ref={markerGroupRef}>
            {locations.map(location => (
                <Marker
                    key={location.id ?? location.name}
                    position={new LatLng(location.latitude, location.longitude)}
                    eventHandlers={{
                        add: showAllMarkersOnMap,
                    }}>
                    <Popup>{location.name}</Popup>
                </Marker>
            ))}
        </FeatureGroup>
    );
}