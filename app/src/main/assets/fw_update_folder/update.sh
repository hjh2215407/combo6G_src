echo "[PACT] WLAN power management off"
iw wlan0 set power_save off

echo "[PACT] Update Check"

# Init SSID
SSID=`cat /etc/hostapd_pact.conf | grep ssid | awk -F'=' '{print $2}'`
if [[ $SSID == 'NONE' ]]
then
    echo "Init SSID"
    /run/media/mmcblk0p1/fw/ssid.sh
    sync
    sync
    reboot
fi

MMC_PATH=/run/media/mmcblk0p1
FW_PATH=${MMC_PATH}/fw

RUN_FILE=${MMC_PATH}/pact-linux-app.elf

FW_FILES=("$FW_PATH/pact-linux-app.elf" "$FW_PATH/vswr.bit.bin" "$FW_PATH/sa.bit.bin" "$FW_PATH/lte5g.bin")
MD5_FILES=("$FW_PATH/elf.md5" "$FW_PATH/vswr.md5" "$FW_PATH/sa.md5" "$FW_PATH/lte5g.md5")

clean_update_files() {
    echo "[PACT] Clean fw update files"
    rm ${FW_FILES[@]}
    rm ${MD5_FILES[@]}
}

echo "[PACT] Checking if there are update files"

for i in "${FW_FILES[@]}";
do
    if [ ! -f "$i" ]; then
        echo "[PACT] No Update $i exists"
        exit
    fi
done
echo ""
for i in "${MD5_FILES[@]}";
do
    if [ ! -f "$i" ]; then
        echo "[PACT] $i doesn't exist"
        exit
    fi
done

echo ""
echo "[PACT] MD5 Checking..."

for i in "${MD5_FILES[@]}";
do
MD5_FILE="$i";
    md5sum -c $MD5_FILE
    if [ $? == 0 ]; then
        echo "[PACT] md5sum is Okay"
    else
        echo "[PACT] md5sum is Fail"
        clean_update_files
        exit
    fi
done

echo ""
echo "[PACT] Starts Updating..."

# Copy All FW & FPGAs
cp ${FW_FILES[@]} $MMC_PATH
chmod +x $RUN_FILE

clean_update_files
