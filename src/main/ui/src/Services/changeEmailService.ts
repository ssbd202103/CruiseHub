import { ChangeEmail } from '../interfaces/changeInterfaces'
import axios from './URL'

export function changeEmail(etag: string, dto: ChangeEmail) {
    axios.put('account/change_email',
        {
            dto
        }, {
            headers: {
                "If-Match": etag
            }
        }).then(res => {
            alert("Email zostal zmieniony")
    }).catch(err => {
        alert(`Blad!\n${err}`)
    })
}