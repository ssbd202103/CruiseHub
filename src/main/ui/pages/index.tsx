import Link from 'next/link'
import en from '../locales/en'
import pl from '../locales/pl'


export default function Home() {
    const lang = pl // this should be taken from account preferences

    return (
        <div>
            <h1>Hello, Next.js with Redux!</h1>
            <Link href="/example">Go to example page</Link> <br/>
            <Link href="/sign-up">{lang.mainPage.signUp}</Link> 
        </div>
    )
}
