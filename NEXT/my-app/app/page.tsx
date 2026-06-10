import Image from "next/image";
import Link from "next/link";
import ProductCard from "./components/ProductCard";

export default function Home() {
  return (
    <main>
      <h2>Hello Next Js World</h2>
      <Link href="./users">User로 이동</Link>
      <ProductCard />
    </main>

  );

}
