import {useState} from 'react'

import Box from '@material-ui/core/Box'
import Button from "@material-ui/core/Button";

import {useTranslation} from 'react-i18next'

export default function ChangeAccountData() {
    const {t} = useTranslation()

    const [ChangePerData, setPerData] = useState(false)
    const [ChangAddress, setChangChangAddress] = useState(false)
    const [ChangEmail, setChangEmail] = useState(false)


    const handleChangePerData = () => {
        setPerData(state => !state)
    }
    const changePersonalData = () => {
        //Place for transfer function (change personal data in database)
        handleChangePerData()
    }

    //Functions for address data change
    const handleChangAddress = () => {
        setChangChangAddress(state => !state)
    }
    const changeAddress = () => {
        //Place for transfer function (change address in database)
        handleChangAddress()
    }

    //Functions for email data change
    const handleChangEmail = () => {
        setChangEmail(state => !state)
    }
    const changeEmail = () => {
        //Place for transfer function (change email in database)
        handleChangEmail()
    }


    return (
        <Box>
            <div>
                <div style={{display: ChangePerData ? "none" : "block"}}>
                    <h3>{t("personalData")}</h3>
                    <h4>{t("name")}</h4><p>Michał</p>
                    <h4>{t("surname")}</h4><p>Blazymur</p>
                    <h4>{t("Login")}</h4><p>BigMike</p>
                    <h4>{t("phoneNumber")}</h4><p>+48-767-765-456</p>
                    <Button variant="contained"
                            onClick={handleChangePerData}
                    >{t("personalDataChange")}</Button>
                </div>
                <div style={{display: ChangePerData ? "block" : "none"}}>
                    <h3>{t("personalDataChange")}</h3>
                    <input type="text" placeholder="Michał"/>
                    <input type="text" placeholder="Blazymur"/>
                    <input type="text" placeholder="BigMike"/>
                    <input type="text" placeholder="+48-767-765-456"/>
                    <Button variant="contained"
                            onClick={changePersonalData}
                    >{t("confirm")}</Button>
                </div>

                <div style={{display: ChangAddress ? "none" : "block"}}>
                    <h3>{t("address")}</h3>
                    <h4>{t("street")}</h4><p>11-Listopada 28</p>
                    <h4>{t("houseNumber")}</h4><p>23</p>
                    <h4>{t("postalCode")}</h4><p>91-345</p>
                    <h4>{t("city")}</h4><p>Zakopianka</p>
                    <h4>{t("country")}</h4><p>Zanzibar</p>
                    <Button variant="contained"
                            onClick={handleChangAddress}
                    >{t("addressChange")}</Button>
                </div>
                <div style={{display: ChangAddress ? "block" : "none"}}>
                    <h3>{t("addressChange")}</h3>
                    <input type="text" placeholder="11-Listopada 28"/>
                    <input type="text" placeholder="23"/>
                    <input type="text" placeholder="91-345"/>
                    <input type="text" placeholder="Zakopianka"/>
                    <input type="text" placeholder="Zanzibar"/>
                    <Button variant="contained"
                            onClick={changeAddress}
                    >{t("confirm")}</Button>
                </div>

                <div style={{display: ChangEmail ? "none" : "block"}}>
                    <h3>{t("email")}</h3>
                    <h4>{t("email")}</h4><p>BigMIke@gmail.com</p>
                    <Button variant="contained"
                            onClick={handleChangEmail}
                    >{t("emailChange")}</Button>
                </div>
                <div style={{display: ChangEmail ? "block" : "none"}}>
                    <h3>{t("emailChange")}</h3>
                    <input type="text" placeholder={t("newEmail")}/>
                    <input type="text" placeholder={t("newEmailConfirm")}/>
                    <Button variant="contained"
                            onClick={changeEmail}
                    >{t("confirm")}</Button>
                </div>
            </div>
        </Box>
    )
}