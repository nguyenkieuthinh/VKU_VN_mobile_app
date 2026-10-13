#!/usr/bin/env bash
# =============================================================================
#  DANH SÁCH TÀI NGUYÊN ĐÃ TẢI / CÒN THIẾU
#  VKU · Lập trình ứng dụng di động (Android + Jetpack Compose)
#
#  Cách dùng:   bash danh_sach_da_tai.sh
#
#  Script này CHỈ ĐỌC, không tải và không sửa gì cả.
#  Nó quét máy để đối chiếu với những gì 5 project Lab 2–6 yêu cầu.
#
#  Snapshot lần quét đầu: 2026-10-13
# =============================================================================

set -u

SDK="${ANDROID_HOME:-${ANDROID_SDK_ROOT:-$HOME/Android/Sdk}}"
GRADLE_HOME_DIR="${GRADLE_USER_HOME:-$HOME/.gradle}"
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# --- Các project trong repo ---------------------------------------------------
LABS=(
  "Lab 2. Business Card app"
  "Lab 3. Dice Roller app"
  "Lab 4. Tip Calculator app"
  "Lab 5. Woof app"
  "Lab 6. Cupcake app"
)

TB=$'\033[1m'; OK=$'\033[32m'; BAD=$'\033[31m'; WARN=$'\033[33m'; NC=$'\033[0m'

tieu_de() { printf '\n%s── %s %s\n' "$TB" "$1" "$NC"; }
co()      { printf "  ${OK}[✓]${NC} %s\n" "$1"; }
thieu()   { printf "  ${BAD}[✗]${NC} %s\n" "$1"; }
luu_y()   { printf "  ${WARN}[!]${NC} %s\n" "$1"; }
tin()     { printf '      %s\n' "$1"; }

MISSING=()   # mô tả những thứ còn thiếu
CAN_TAI=()   # lệnh / gói cần tải

# =============================================================================
printf '%s' "$TB"
cat <<'BANNER'
=================================================================
  KIỂM TRA TÀI NGUYÊN — VKU MOBILE APP (Android/Compose)
=================================================================
BANNER
printf '%s' "$NC"
printf '  Repo : %s\n' "$ROOT"
printf '  SDK  : %s\n' "$SDK"

# =============================================================================
tieu_de "1. JDK (bắt buộc: 17 hoặc 21)"

JDK_OK=0
if command -v java >/dev/null 2>&1; then
  RAW="$(java -version 2>&1 | head -1)"
  VER="$(printf '%s' "$RAW" | sed -n 's/.*version "\([0-9]*\).*/\1/p')"
  [ -z "$VER" ] && VER=0
  if [ "$VER" -ge 17 ] && [ "$VER" -le 21 ]; then
    co "java $VER  → $RAW"
    JDK_OK=1
  elif [ "$VER" -gt 21 ]; then
    luu_y "java $VER — QUÁ MỚI, Gradle 8.14.4 chỉ chạy tới Java 24"
    tin "$RAW"
    MISSING+=("JDK 17 hoặc 21 (đang có Java $VER)")
    CAN_TAI+=("paru -S jdk17-openjdk   # hoặc jdk21-openjdk")
  else
    luu_y "java $VER — quá cũ, cần >= 17"
    MISSING+=("JDK >= 17")
  fi
else
  thieu "Không tìm thấy java trong PATH"
  MISSING+=("JDK 17/21")
  CAN_TAI+=("paru -S jdk17-openjdk")
fi

if [ -x /opt/android-studio/jbr/bin/java ]; then
  JBR="$(/opt/android-studio/jbr/bin/java -version 2>&1 | head -1)"
  luu_y "Android Studio JBR: $JBR"
  tin "JBR 25 cũng vượt mức Gradle 8.14.4 hỗ trợ — đừng trỏ JAVA_HOME vào đây"
fi

# =============================================================================
tieu_de "2. Android SDK — gói đã tải"

if [ -d "$SDK" ]; then
  co "Thư mục SDK tồn tại: $SDK"
else
  thieu "Chưa có $SDK  → cần cài Android SDK"
  MISSING+=("Android SDK tại $SDK")
fi

kiem_tra_goi() {  # $1 = đường dẫn tương đối, $2 = mô tả
  if [ -e "$SDK/$1" ]; then co "$2  ($1)"; else thieu "$2  ($1)"; MISSING+=("$2"); return 1; fi
}

kiem_tra_goi "platform-tools/adb"           "platform-tools (adb)"        || CAN_TAI+=("sdkmanager 'platform-tools'")
kiem_tra_goi "emulator/emulator"            "emulator"                    || CAN_TAI+=("sdkmanager 'emulator'")

# --- platform: project compileSdk = 35 (BẮT BUỘC) ----------------------------
if [ -d "$SDK/platforms/android-35" ]; then
  co "platform android-35  ← compileSdk của cả 5 lab"
else
  thieu "platform android-35  ← compileSdk = 35, BẮT BUỘC"
  MISSING+=("platforms;android-35")
  CAN_TAI+=("sdkmanager 'platforms;android-35'")
fi

printf '      Các platform hiện có:'
if [ -d "$SDK/platforms" ] && [ -n "$(ls -A "$SDK/platforms" 2>/dev/null)" ]; then
  printf ' %s\n' "$(ls "$SDK/platforms" | tr '\n' ' ')"
else
  printf ' (không có)\n'
fi

# --- build-tools -------------------------------------------------------------
if [ -d "$SDK/build-tools/35.0.0" ]; then
  co "build-tools 35.0.0  ← mặc định của AGP 8.7.3"
else
  luu_y "Thiếu build-tools 35.0.0 (AGP 8.7.3 mặc định dùng bản này)"
  MISSING+=("build-tools;35.0.0")
  CAN_TAI+=("sdkmanager 'build-tools;35.0.0'")
fi
printf '      Các build-tools hiện có:'
if [ -d "$SDK/build-tools" ] && [ -n "$(ls -A "$SDK/build-tools" 2>/dev/null)" ]; then
  printf ' %s\n' "$(ls "$SDK/build-tools" | tr '\n' ' ')"
else
  printf ' (không có)\n'
fi

# --- system image + AVD ------------------------------------------------------
if [ -n "$(find "$SDK/system-images" -name "x86_64" -maxdepth 3 2>/dev/null)" ]; then
  co "system-image x86_64  ($(ls "$SDK/system-images" 2>/dev/null | tr '\n' ' '))"
else
  thieu "Chưa có system-image cho emulator"
  MISSING+=("system-images")
  CAN_TAI+=("sdkmanager 'system-images;android-35;google_apis;x86_64'")
fi

if [ -d "$HOME/.android/avd" ] && [ -n "$(ls -A "$HOME/.android/avd" 2>/dev/null)" ]; then
  co "AVD đã tạo: $(ls "$HOME/.android/avd" | grep '\.ini$' | sed 's/\.ini$//' | tr '\n' ' ')"
else
  luu_y "Chưa có AVD (máy ảo) nào"
  MISSING+=("AVD emulator")
fi

# --- sdkmanager (cmdline-tools) ---------------------------------------------
if [ -x "$SDK/cmdline-tools/latest/bin/sdkmanager" ]; then
  co "cmdline-tools (sdkmanager) — cài gói bằng dòng lệnh được"
else
  thieu "CHƯA có cmdline-tools → không có sdkmanager để tải gói qua CLI"
  tin "Cài trong Android Studio: Settings → Languages & Frameworks → Android SDK"
  tin "    → tab 'SDK Tools' → tick 'Android SDK Command-line Tools (latest)' → Apply"
  MISSING+=("cmdline-tools (bắt buộc cài TRƯỚC để có sdkmanager)")
fi

# --- licenses ----------------------------------------------------------------
if [ -f "$SDK/licenses/android-sdk-license" ]; then
  co "SDK license đã chấp nhận"
else
  luu_y "Chưa chấp nhận license SDK → gradle build sẽ dừng"
  CAN_TAI+=("sdkmanager --licenses")
fi

# --- NDK: không cần cho các lab này -----------------------------------------
printf '      NDK: không cần (các lab này thuần Kotlin/Compose)\n'

# =============================================================================
tieu_de "3. Gradle"

if [ -d "$GRADLE_HOME_DIR/wrapper/dists" ] && \
   [ -n "$(ls -A "$GRADLE_HOME_DIR/wrapper/dists" 2>/dev/null)" ]; then
  co "Gradle wrapper dist đã tải: $(ls "$GRADLE_HOME_DIR/wrapper/dists" | tr '\n' ' ')"
else
  thieu "CHƯA tải Gradle 8.14.4 (~130 MB) — lần build đầu sẽ tự tải"
  MISSING+=("Gradle 8.14.4 distribution")
fi

if [ -d "$GRADLE_HOME_DIR/caches/modules-2" ]; then
  co "Cache thư viện Gradle đã có ($(du -sh "$GRADLE_HOME_DIR/caches" 2>/dev/null | cut -f1))"
else
  thieu "Cache thư viện trống → lần build đầu tải AGP 8.7.3, Kotlin 2.0.21, Compose BOM 2024.12.01 (~500 MB–1 GB)"
  MISSING+=("Cache dependencies Maven/Google (~nhỏ nhất vài trăm MB)")
fi

if command -v gradle >/dev/null 2>&1; then
  co "gradle hệ thống: $(gradle --version 2>/dev/null | sed -n 's/^Gradle //p' | head -1)"
else
  luu_y "Không có gradle hệ thống — KHÔNG sao, dùng ./gradlew trong từng lab là đủ"
fi

# =============================================================================
tieu_de "4. IDE"

if [ -d /opt/android-studio ]; then
  co "Android Studio: $(sed -n 's/.*"version": "\([^"]*\)".*/\1/p' /opt/android-studio/product-info.json 2>/dev/null | head -1)"
else
  luu_y "Không thấy Android Studio ở /opt/android-studio"
fi

# =============================================================================
tieu_de "5. Project trong repo"

for lab in "${LABS[@]}"; do
  if [ ! -d "$ROOT/$lab" ]; then
    thieu "$lab — không tồn tại"
    continue
  fi

  SO_FILE="$(find "$ROOT/$lab" -type f 2>/dev/null | wc -l)"
  if [ "$SO_FILE" -eq 0 ]; then
    luu_y "$lab — THƯ MỤC RỖNG (0 file)"
    MISSING+=("Mã nguồn $lab")
    continue
  fi

  # gradlew phải có quyền thực thi
  if [ -x "$ROOT/$lab/gradlew" ]; then P="ok"; else P="THIẾU +x"; fi

  # local.properties trỏ tới SDK
  if grep -qs '^sdk.dir=' "$ROOT/$lab/local.properties"; then L="có"; else L="CHƯA có"; fi

  printf '  %s\n' "$lab"
  printf '      %s file · gradlew:%s · local.properties:%s\n' "$SO_FILE" "$P" "$L"
  [ "$L" = "CHƯA có" ] && MISSING+=("local.properties trong '$lab'")
done

# Lab 6 bị ghi trong git như một gitlink rỗng (mode 160000, không có .gitmodules)
if [ "$(git -C "$ROOT" ls-files -s "Lab 6. Cupcake app" 2>/dev/null | awk '{print $1}')" = "160000" ]; then
  luu_y "'Lab 6. Cupcake app' là gitlink 160000 nhưng KHÔNG có .gitmodules"
  tin "→ mã nguồn Lab 6 không có trong repo; cần thêm lại thư mục bình thường"
fi

printf '      Thư mục trống khác:'
for d in "Lab 7." "Lab 8."; do
  [ -d "$ROOT/$d" ] && [ -z "$(ls -A "$ROOT/$d" 2>/dev/null)" ] && printf ' %s' "$d"
done
printf '\n'

# =============================================================================
tieu_de "6. Thiết bị / mạng"

ADB="$SDK/platform-tools/adb"
if [ -x "$ADB" ]; then
  TB_LINE="$("$ADB" devices 2>/dev/null | tail -n +2 | grep -c 'device$')"
  if [ "$TB_LINE" -gt 0 ]; then co "Có $TB_LINE thiết bị/emulator đang kết nối"; else luu_y "Chưa có thiết bị nào kết nối (adb devices trống)"; fi
fi

if curl -s -o /dev/null --max-time 8 https://dl.google.com/android/repository/repository2-3.xml 2>/dev/null; then
  co "Mạng tới dl.google.com: OK (tải được SDK)"
else
  luu_y "Không kết nối được dl.google.com — kiểm tra mạng/proxy"
fi

# =============================================================================
printf '\n%s=================================================================%s\n' "$TB" "$NC"
printf '%s  TỔNG KẾT%s\n' "$TB" "$NC"
printf '%s=================================================================%s\n' "$TB" "$NC"

if [ "${#MISSING[@]}" -eq 0 ]; then
  printf "${OK}  Mọi thứ đã sẵn sàng — build được ngay.%s\n" "$NC"
else
  printf "${BAD}  Còn THIẾU %d hạng mục:%s\n" "${#MISSING[@]}" "$NC"
  i=0
  for m in "${MISSING[@]}"; do
    i=$((i+1)); printf '   %2d. %s\n' "$i" "$m"
  done
fi

if [ "${#CAN_TAI[@]}" -gt 0 ]; then
  printf '\n%s  LỆNH CẦN CHẠY (theo thứ tự)%s\n' "$TB" "$NC"
  printf '    # 0. Cài cmdline-tools trong Android Studio trước — có nó mới có sdkmanager\n'
  for c in "${CAN_TAI[@]}"; do printf '    %s\n' "$c"; done
fi

cat <<'GOI_Y'

  ── Sau khi đủ tài nguyên ─────────────────────────────────────
    cd "Lab 5. Woof app"
    echo "sdk.dir=$HOME/Android/Sdk" > local.properties
    export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
    ./gradlew assembleDebug          # build APK
    ./gradlew installDebug           # cài lên emulator/điện thoại
GOI_Y
printf '\n'
