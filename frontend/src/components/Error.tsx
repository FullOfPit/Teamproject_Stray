import {Link} from "react-router-dom";

export default function Error({message, link}: {
    message: string,
    link?: {
        text: string,
        to: string
    }
}) {
    return (
      <div className="error-message-container">
          <p>{message}</p>
          {link &&
            <Link to={link.to}>{link.text}</Link>
          }
      </div>
    );
}