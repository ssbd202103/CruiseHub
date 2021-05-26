import store from "../redux/store";
import axios from "../Services/URL"
import {update} from "../redux/slices/tokenSlice";
import {setUser, emptyUser} from "../redux/slices/userSlice";

export function getUser(token: string) {
    return axios.get('self/account-details', {
        headers: {
            Authorization: token && `Bearer ${token}`
        }
    }).then(res => {
        store.dispatch(update(token))
        store.dispatch(setUser(res.data))
    })
}

export function logOut() {
    store.dispatch(emptyUser())
}