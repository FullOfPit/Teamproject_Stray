import "./AddLocationForm.css";
import Location from "../types/Location";
import Button from "react-bootstrap/Button"

export default function AddLocationForm({
    onAdd
}:{
    onAdd: (location:Location) => void}
){
    // show input for search term
    // request openstreetmap API for terms
    // display FindLocationOverlay with searchresults

    //dummy location to test trip form page-axios post
    const location: Location=  {
        "name": "Kölner Dom",
        "latitude": 50.941386546092225,
        "longitude": 6.958270670147375
    };

    return (
      <div className={"add-location-form"}>
          <p>I am the form for adding locations</p>
          <Button variant="light" onClick={() => onAdd(location)}>Add</Button>
      </div>
    );
}