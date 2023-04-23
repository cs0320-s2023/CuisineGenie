// The code for this component and its usage was taken from this Stack Overflow post: https://stackoverflow.com/questions/36904185/react-router-scroll-to-top-on-every-transition#comment124980646_61602724
import { useEffect } from "react";
import { useLocation } from "react-router-dom";

export default function ScrollToTop() {
  const { pathname } = useLocation();

  useEffect(() => {
    window.scrollTo(0, 0);
  }, [pathname]);

  return null;
}
