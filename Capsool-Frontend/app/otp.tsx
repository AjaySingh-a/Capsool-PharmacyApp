import { useState } from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  Alert,
  TextInput,
} from 'react-native';
import { useRouter } from 'expo-router';
import { setLoggedIn } from '@/hooks/useAuth';

export default function OTPScreen() {
  const [otp, setOTP] = useState('');
  const router = useRouter();

  const handleVerifyOTP = () => {
    if (otp.length === 6) {
      setLoggedIn(true);        // mark user as logged in
      router.replace('/');      // go to root—RootLayout then shows tabs
    } else {
      Alert.alert('Invalid OTP', 'Please enter a valid 6-digit OTP');
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Enter OTP</Text>

      {/* Single TextInput for web/native stability */}
      <TextInput
        value={otp}
        onChangeText={setOTP}
        maxLength={6}
        keyboardType="number-pad"
        style={styles.input}
        placeholder="Enter 6-digit OTP"
      />

      <TouchableOpacity style={styles.button} onPress={handleVerifyOTP}>
        <Text style={styles.buttonText}>Verify OTP</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 24,
    justifyContent: 'center',
    backgroundColor: '#fff',
  },
  title: {
    fontSize: 22,
    fontWeight: '600',
    marginBottom: 24,
    textAlign: 'center',
  },
  input: {
    borderWidth: 1,
    borderColor: '#999',
    borderRadius: 8,
    padding: 14,
    fontSize: 20,
    textAlign: 'center',
    marginBottom: 32,
  },
  button: {
    backgroundColor: '#0084ff',
    paddingVertical: 14,
    borderRadius: 8,
  },
  buttonText: {
    color: '#fff',
    fontSize: 17,
    fontWeight: '500',
    textAlign: 'center',
  },
});
