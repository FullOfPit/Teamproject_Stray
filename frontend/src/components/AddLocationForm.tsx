import "./AddLocationForm.css";
import Location from "../types/Location";

export default function AddLocationForm({
    onAdd
}:{
    onAdd: (addLocation:Location) => void
}) {

    const [searchLocation, setSearchLocation] = useState<string>("");
    const [searchResults, setSearchResults] = useState<SearchResult[]>([]);

    const search = async (searchedLocation: String) => {
        const response = await axios.get("https://nominatim.openstreetmap.org/search", {
            params: {
                q: searchedLocation,
                "accept-language": "de-de",
                format: "jsonv2"
            }
        })

        return response.data;
    }

    const onSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const searchResults = await search(searchLocation);
        setSearchResults(searchResults);
    };

    const onLocationAdd = (location:Location) =>{
        setSearchLocation("");
        onAdd(location);
    }

    return (
        <>
            <p>Add location</p>

            <form onSubmit={onSubmit}>
                <input value={searchLocation} placeholder={"search location..."}
                       onChange={(e) => setSearchLocation(e.target.value)}/>
                <button>Search</button>
            </form>

            <br/>
            {searchResults.length > 0 &&
                <FindLocationOverlay searchResults={searchResults} onAdd={onLocationAdd}
                                     onCancel={() => setSearchLocation("")}/>}
        </>
    );
}