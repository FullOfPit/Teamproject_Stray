import {FormEvent, useState} from "react";
import axios from "axios";
import Place from "../types/openstreetmap/Place";

export default function MapSearch() {
    const [query, setQuery] = useState<String>("");
    const [searchResults, setSearchResults] = useState<Place[]>([]);

    /**
     * SEARCH
     * siehe https://nominatim.org/release-docs/develop/api/Search/
     *
     * - GET Request an https://nominatim.openstreetmap.org/search?q=<url-encoded-search-term>&accept-language=de-de&format=jsonv2
     *         e.g. https://nominatim.openstreetmap.org/search.php?q=K%C3%B6lner%20Dom&accept-language=de-de&format=jsonv2
     * - Response:
     * - [
     *      {
     *          "place_id": 102939483,
     *          "lat": "50.941303500000004",
     *          "lon": "6.958137997831819",
     *          "display_name": "Kölner Dom, 4, Domkloster, Andreasviertel, Altstadt-Nord, Innenstadt, Köln, Nordrhein-Westfalen, 50667, Deutschland",
     *          ...
     *      },
     *      {
     *          "place_id": 209973588,
     *          "lat": "8.932560599999999",
     *          "lon": "38.77150273130003",
     *          "display_name": "Kölner DOm, 4, ቃበና, Akaki Kaliti, አዲስ አበባ / Addis Ababa, Addis Abeba, 0006, Äthiopien",
     *          ...
     *      }
     *   ]
     */

    const search = async (query: String) => {
        const response = await axios.get("https://nominatim.openstreetmap.org/search", {
            params: {
                q: query,
                "accept-language": "de-de",
                format: "jsonv2"
            }
        })

        return response.data;
    }

    const onSubmit = async (e: FormEvent) => {
        e.preventDefault();

        const searchResults = await search(query);
        setSearchResults(searchResults);
    }

    const onSelect = (element: HTMLOptionElement) => {
        console.log(element.dataset);
    }

    return (
        <div>
            <form onSubmit={onSubmit}>
                <input onChange={e => setQuery(e.target.value)} list="suggestions" />
                <button type="submit">Search</button>
            </form>
            <div>
                <datalist id="suggestions">
                    {searchResults.map(place => (
                        <option key={place.place_id} data-lan={place.lat} data-lon={place.lon}>{place.display_name}</option>
                    ))}
                </datalist>
            </div>
        </div>
    )
}