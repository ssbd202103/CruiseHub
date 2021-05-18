import store from '../redux/store';
import axios from './URL'

export default function getAllAccounts() {
    const {token} = store.getState()

    return axios.get('account/accounts', {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
}