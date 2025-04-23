import React, { useEffect } from 'react';
import { View, Text } from 'react-native';
import { DarkTheme, DefaultTheme, ThemeProvider } from '@react-navigation/native';
import { useFonts } from 'expo-font';
import { Slot, useRouter, useSegments } from 'expo-router';
import * as SplashScreen from 'expo-splash-screen';
import { StatusBar } from 'expo-status-bar';
import 'react-native-reanimated';

import { useColorScheme } from '@/hooks/useColorScheme';
import { useAuth } from '@/hooks/useAuth';

// Keep splash visible until ready
SplashScreen.preventAutoHideAsync();

export default function RootLayout() {
  const colorScheme = useColorScheme();
  const router = useRouter();
  const segments = useSegments();
  const [loaded] = useFonts({
    SpaceMono: require('../assets/fonts/SpaceMono-Regular.ttf'),
  });

  const { isLoggedIn, isLoading } = useAuth();

  // Hide splash when fonts load
  useEffect(() => {
    if (loaded) {
      SplashScreen.hideAsync();
    }
  }, [loaded]);

  // Navigate based on auth state
  useEffect(() => {
    if (isLoading) return;

    // If not logged in & not on login/otp, redirect to login
    if (!isLoggedIn && segments[0] !== 'login' && segments[0] !== 'otp') {
      router.replace('/login');
      return;
    }
    // If logged in & on login/otp, redirect to home
    if (isLoggedIn && (segments[0] === 'login' || segments[0] === 'otp')) {
      router.replace('/');
    }
  }, [isLoading, isLoggedIn, segments]);

  // Show loading fallback
  if (!loaded || isLoading) {
    return (
      <ThemeProvider value={colorScheme === 'dark' ? DarkTheme : DefaultTheme}>
        <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
          <Text>Loading…</Text>
        </View>
      </ThemeProvider>
    );
  }

  // Render content via Slot
  return (
    <ThemeProvider value={colorScheme === 'dark' ? DarkTheme : DefaultTheme}>
      <Slot />
      <StatusBar style="auto" />
    </ThemeProvider>
  );
}
