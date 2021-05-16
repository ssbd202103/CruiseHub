import store from "../redux/store";
import axios from "axios";
import {update} from "../redux/slices/tokenSlice";
import {setUser} from "../redux/slices/userSlice";

export default async function getUser(token: string) {
    const accountDetails = await axios.get('http://localhost:8080/api/self/account-details', {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })

    store.dispatch(update(token))
    store.dispatch(setUser(accountDetails.data))
}