import {useHistory, useLocation} from 'react-router-dom';

import axios from "axios";
import AuthLayout from "../../layouts/AuthLayout";
import {useTranslation} from "react-i18next";
import styles from '../../styles/auth.global.module.css'
import RoundedButton from "../../components/RoundedButton";

function VerifyAccount(props: any) {
    const location = useLocation();
    const {t} = useTranslation()
    const history = useHistory()
    const submitAccountVerification = async (event: any) => {
        event.preventDefault()

        const json = {
            "token": location.pathname.toString().substring('/verify/accountVerification/'.length)
        }

        await axios.put('http://localhost:8080/api/account/verify', json, {
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(res =>{history.push("/signin")});
    }


    return (
        <AuthLayout>
            <div>
                <form onSubmit={submitAccountVerification}>
                    <h1 className={styles.h1}>{t("verifyAccount")}</h1>
                        <RoundedButton
                            onClick={submitAccountVerification}
                            style={{width: '100%', fontSize: '1.2rem', padding: '10px 0', marginBottom: 20}}
                            color="pink"
                        >{t("verifyAccount")} </RoundedButton>
                </form>
            </div>
        </AuthLayout>
    );
}

export default VerifyAccount;
