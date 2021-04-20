import Head from 'next/head'

import 'normalize.css'
import '../styles/globals.css'


function SafeHydrate({children}) {
    return (
        <div suppressHydrationWarning>
            {typeof window === 'undefined' ? null : children}
        </div>
    )
}

function App({Component, pageProps}) {
    return (
        <>
        <Head>
            <title>CruiseHub</title>
            <meta name="viewport" content="initial-scale=1.0, width=device-width" />
        </Head>
        <SafeHydrate><Component {...pageProps} /></SafeHydrate>
        </>
    )
}

export default App
