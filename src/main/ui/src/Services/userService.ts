import store from "../redux/store";
import axios from "axios";
import {update} from "../redux/slices/tokenSlice";
import {setUser, emptyUser} from "../redux/slices/userSlice";

export function getUser(token: string) {
    return axios.get('/api/self/account-details', {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    }).then(res => {
        store.dispatch(update(token))
        store.dispatch(setUser(res.data))
    })
}

export function logOut() {
    store.dispatch(emptyUser())
}