import Head from 'next/head'

import 'normalize.css'
import '../styles/globals.css'
import { I18nextProvider } from 'react-i18next'
import i18n from '../i18n/i18n'

function SafeHydrate({children}) {
    return (
        <div suppressHydrationWarning>
            {typeof window === 'undefined' ? null : children}
        </div>
    )
}

function App({Component, pageProps}) {
    return (
        <I18nextProvider i18n={i18n}>
            <Head>
                <title>CruiseHub</title>
                <meta name="viewport" content="initial-scale=1.0, width=device-width" />
            </Head>
            <SafeHydrate><Component {...pageProps} /></SafeHydrate>
        </I18nextProvider>
    )
}

export default App
