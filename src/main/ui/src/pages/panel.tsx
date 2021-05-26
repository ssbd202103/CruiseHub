import {useSelector} from "react-redux";
import {selectActiveAccessLevel} from "../redux/slices/userSlice";
import AdminPanel from "./panels/adminPanel";
import ModeratorPanel from "./panels/moderatorPanel";
import ClientPanel from "./panels/clientPanel";
import WorkerPanel from "./panels/workerPanel";
import Home from "./home";


export default function Panel() {
    const activeAccessLevel = useSelector(selectActiveAccessLevel)

    switch (activeAccessLevel) {
        case "ADMINISTRATOR":
            return <AdminPanel />
        case "MODERATOR":
            return <ModeratorPanel />
        case "BUSINESS_WORKER":
            return <WorkerPanel />
        default:
            return <Home />
    }
}