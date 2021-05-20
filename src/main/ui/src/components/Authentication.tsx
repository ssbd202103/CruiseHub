import {useSelector} from "react-redux";
import {isUserEmpty} from "../redux/slices/userSlice";
import {Link} from "react-router-dom";
import styles from "../styles/Header.module.css";
import RoundedButton from "./RoundedButton";
import Box from "@material-ui/core/Box";
import {useTranslation} from "react-i18next";


export default function Authentication() {
    const {t} = useTranslation()
    const isEmpty = useSelector(isUserEmpty)

    return (
        <Box>

            {/*<Link to="panels/adminPanel">*/}
            {/*    <a style={{marginRight: 20}} className={styles.link}>{t("adminPanel")}</a>*/}
            {/*</Link>*/}
            {/*<Link to="panels/moderatorPanel">*/}
            {/*    <a style={{marginRight: 20}} className={styles.link}>{t("moderatorPanel")}</a>*/}
            {/*</Link>*/}
            {/*<Link to="panels/clientPanel">*/}
            {/*    <a style={{marginRight: 20}} className={styles.link}>{t("clientPanel")}</a>*/}
            {/*</Link>*/}
            {/*<Link to="panels/workerPanel">*/}
            {/*    <a style={{marginRight: 20}} className={styles.link}>{t("workerPanel")}</a>*/}
            {/*</Link>*/}

            <Link to="signin">
                <a style={{marginRight: 20}} className={styles.link}>{t("signin")}</a>
            </Link>

            <Link to="signup/client">
                <RoundedButton
                    style={{
                        fontSize: '1rem',
                        padding: '10px 20px',
                        textTransform: 'none'
                    }}
                    color="pink"
                >
                    {t("signup")}
                </RoundedButton>
            </Link>
        </Box>
    )
}