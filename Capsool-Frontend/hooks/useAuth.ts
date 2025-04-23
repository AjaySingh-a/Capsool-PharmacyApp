import { useEffect, useState } from 'react';

let setLoginStateExternal: (val: boolean) => void = () => {};

export function useAuth() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const timer = setTimeout(() => {
      const fakeLoginState = false;
      setIsLoggedIn(fakeLoginState);
      setIsLoading(false);
    }, 1000);

    setLoginStateExternal = setIsLoggedIn;

    return () => clearTimeout(timer);
  }, []);

  return { isLoggedIn, isLoading };
}

export function setLoggedIn(value: boolean) {
  setLoginStateExternal(value);
}
