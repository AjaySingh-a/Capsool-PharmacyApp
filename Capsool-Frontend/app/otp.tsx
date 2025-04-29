import { useState, useEffect } from 'react';
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  KeyboardAvoidingView,
  Platform,
  StyleSheet,
  Image,
  Animated,
} from 'react-native';
import { useRouter } from 'expo-router';
import { setLoggedIn } from '@/hooks/useAuth';

export default function OTPScreen() {
  const [otp, setOTP] = useState('');
  const [timer, setTimer] = useState(60);
  const [canResend, setCanResend] = useState(false);
  const fadeAnim = useState(new Animated.Value(0))[0]; // for fade-in
  const router = useRouter();

  useEffect(() => {
    const interval = setInterval(() => {
      setTimer(t => {
        if (t <= 1) {
          clearInterval(interval);
          setCanResend(true);
          Animated.timing(fadeAnim, {
            toValue: 1,
            duration: 500,
            useNativeDriver: true,
          }).start();
          return 0;
        }
        return t - 1;
      });
    }, 1000);
    return () => clearInterval(interval);
  }, []);

  const handleVerifyOTP = () => {
    if (otp.length === 6) {
      setLoggedIn(true);
      router.replace('/');
    } else {
      alert('Please enter a valid 6-digit OTP');
    }
  };

  const handleResend = () => {
    if (!canResend) return;
    setTimer(60);
    setCanResend(false);
    fadeAnim.setValue(0);
    // TODO: trigger OTP resend
  };

  return (
    <KeyboardAvoidingView
      style={styles.container}
      behavior={Platform.OS === 'ios' ? 'padding' : undefined}
    >
      <View style={styles.innerContainer}>
        <Image
          source={require('@/assets/images/logo.png')}
          style={styles.logo}
          resizeMode="contain"
        />
        <Text style={styles.title}>Enter Verification Code</Text>
        <Text style={styles.subtitle}>
          We’ve sent a 6-digit code to your phone. Please enter it below.
        </Text>

        <View style={styles.card}>
          <TextInput
            style={styles.input}
            placeholder="••••••"
            keyboardType="number-pad"
            maxLength={6}
            value={otp}
            onChangeText={setOTP}
          />
          <TouchableOpacity style={styles.button} onPress={handleVerifyOTP}>
            <Text style={styles.buttonText}>Verify & Continue</Text>
          </TouchableOpacity>
        </View>

        {/* Fancy Resend Section */}
        <View style={styles.resendWrapper}>
          {!canResend ? (
            <View style={styles.countdownBadge}>
              <Text style={styles.countdownText}>{timer}s</Text>
              <Text style={styles.countdownLabel}>until resend</Text>
            </View>
          ) : (
            <Animated.View style={{ opacity: fadeAnim }}>
              <TouchableOpacity onPress={handleResend} style={styles.resendButton}>
                <Text style={styles.resendButtonText}>Resend Code</Text>
              </TouchableOpacity>
            </Animated.View>
          )}
        </View>
      </View>
    </KeyboardAvoidingView>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#f5f5f5' },
  innerContainer: { flex: 1, justifyContent: 'center', padding: 24 },
  logo: { width: 80, height: 80, alignSelf: 'center', marginBottom: 16 },
  title: { fontSize: 22, fontWeight: '600', textAlign: 'center', color: '#000' },
  subtitle: { fontSize: 14, textAlign: 'center', color: '#555', marginBottom: 24 },

  card: {
    backgroundColor: '#fff',
    borderRadius: 16,
    padding: 24,
    shadowColor: '#000',
    shadowOpacity: 0.05,
    shadowRadius: 8,
    elevation: 2,
    marginBottom: 32,
  },
  input: {
    borderWidth: 1,
    borderColor: '#ccc',
    borderRadius: 12,
    padding: 14,
    fontSize: 18,
    textAlign: 'center',
    letterSpacing: 8,
    backgroundColor: '#f9f9f9',
    marginBottom: 24,
  },
  button: { backgroundColor: '#000', padding: 16, borderRadius: 8 },
  buttonText: { color: '#fff', fontSize: 17, fontWeight: '500', textAlign: 'center' },

  resendWrapper: { alignItems: 'center' },
  countdownBadge: {
    flexDirection: 'row',
    backgroundColor: '#e0e0e0',
    borderRadius: 20,
    paddingVertical: 6,
    paddingHorizontal: 12,
    alignItems: 'center',
  },
  countdownText: { fontSize: 16, fontWeight: '600', marginRight: 6 },
  countdownLabel: { fontSize: 14, color: '#555' },

  resendButton: {
    backgroundColor: '#000',
    paddingVertical: 10,
    paddingHorizontal: 20,
    borderRadius: 20,
  },
  resendButtonText: { color: '#fff', fontSize: 15, fontWeight: '600' },
});
