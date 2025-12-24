#!/bin/bash

echo "=== L1J-TW 編譯腳本（基於 build.xml）==="

# 基本設定（參考 build.xml）
CLASS_DIR="bin"
SERVER_SRC_DIR="src"
LIB_DIR="lib"
ETC_DIR="config"
SERVER_JAR_FILE="l1jserver.jar"

# 清理編譯目錄
echo "🧹 清理既有的編譯檔案..."
rm -rf "$CLASS_DIR"

# 創建編譯目錄
echo "📁 創建編譯目錄..."
mkdir -p "$CLASS_DIR"

# 檢查依賴庫
echo "🔍 檢查依賴庫..."
if [ -d "$LIB_DIR" ]; then
    echo "📚 找到 lib 目錄，包含的 JAR 文件："
    ls -la "$LIB_DIR"/*.jar 2>/dev/null || echo "⚠️  lib 目錄為空"
    CLASSPATH="$LIB_DIR/*"
else
    echo "⚠️  未找到 lib 目錄"
    CLASSPATH="."
fi

# 檢查源碼目錄
echo "🔍 檢查源碼目錄..."
if [ ! -d "$SERVER_SRC_DIR" ]; then
    echo "❌ 錯誤: 找不到源碼目錄 $SERVER_SRC_DIR"
    exit 1
fi

JAVA_FILE_COUNT=$(find "$SERVER_SRC_DIR" -name "*.java" | wc -l)
echo "📄 找到 $JAVA_FILE_COUNT 個 Java 源文件"

# 編譯 Java 源碼（參考 build.xml 的編譯參數）
echo "⚙️  編譯 Java 源碼..."
echo "📋 編譯參數: Java 1.7, UTF-8 編碼, 優化開啟"

# 生成源文件列表
find "$SERVER_SRC_DIR" -name "*.java" > sources.txt

# 執行編譯（參考 build.xml 的 javac 參數）
if javac \
    -cp "$CLASSPATH" \
    -d "$CLASS_DIR" \
    -source 1.7 \
    -target 1.7 \
    -encoding UTF-8 \
    -deprecation \
    -Xlint:unchecked \
    @sources.txt 2>compile.log; then
    
    echo "✅ Java 源碼編譯成功"
    
    # 檢查編譯結果
    CLASS_COUNT=$(find "$CLASS_DIR" -name "*.class" | wc -l)
    echo "📄 生成了 $CLASS_COUNT 個 class 文件"
else
    echo "❌ Java 源碼編譯失敗"
    echo "📋 編譯錯誤信息："
    cat compile.log
    exit 1
fi

# 複製 c3p0 配置文件（參考 build.xml 的 copy_c3p0_config 任務）
echo "📁 複製 c3p0 配置文件..."
if [ -f "$ETC_DIR/c3p0-config.xml" ]; then
    cp "$ETC_DIR/c3p0-config.xml" "$CLASS_DIR/"
    echo "✅ c3p0-config.xml 已複製"
else
    echo "⚠️  未找到 $ETC_DIR/c3p0-config.xml"
fi

# 生成 MANIFEST.MF 的 classpath（參考 build.xml 的 mf.classpath）
echo "📦 準備 MANIFEST.MF..."
if [ -d "$LIB_DIR" ]; then
    MANIFEST_CLASSPATH=""
    for jar in "$LIB_DIR"/*.jar; do
        if [ -f "$jar" ]; then
            JAR_NAME=$(basename "$jar")
            if [ -z "$MANIFEST_CLASSPATH" ]; then
                MANIFEST_CLASSPATH="$LIB_DIR/$JAR_NAME"
            else
                MANIFEST_CLASSPATH="$MANIFEST_CLASSPATH $LIB_DIR/$JAR_NAME"
            fi
        fi
    done
    echo "🔗 Classpath: $MANIFEST_CLASSPATH"
else
    MANIFEST_CLASSPATH=""
fi

# 創建 jar 文件（參考 build.xml 的 jar_server 任務）
echo "📦 創建 jar 文件..."
cd "$CLASS_DIR"

# 創建 MANIFEST.MF 內容（參考 build.xml 的 manifest 屬性）
cat > ../manifest.tmp << EOF
Manifest-Version: 1.0
Class-Path: $MANIFEST_CLASSPATH
Main-Class: l1j.server.Server
Created-By: L1JTW 99nets
Specification-Title: L1JTW_99nets_GameServer_S3DS2
Specification-Vendor: L1JTW 99nets
EOF

# 創建 jar 文件
if jar -cfm "../$SERVER_JAR_FILE" ../manifest.tmp .; then
    echo "✅ JAR 文件創建成功: $SERVER_JAR_FILE"
else
    echo "❌ JAR 文件創建失敗"
    exit 1
fi

cd ..

# 清理臨時文件
echo "🧹 清理臨時文件..."
rm -rf "$CLASS_DIR" sources.txt compile.log manifest.tmp

# 顯示結果
echo ""
echo "🎉 編譯完成！"
echo "📦 生成的 JAR 文件: $SERVER_JAR_FILE"
echo "📊 JAR 文件大小: $(du -h "$SERVER_JAR_FILE" | cut -f1)"
echo "🎯 主類: l1j.server.Server"
echo ""
echo "📋 使用方式:"
echo "  java -jar $SERVER_JAR_FILE"
echo "  或"
echo "  sh ServerStart.sh"
echo ""
echo "🔍 驗證 JAR 內容:"
jar -tf "$SERVER_JAR_FILE" | grep -E "(l1j/server/Server.class|c3p0-config.xml)" && echo "✅ 關鍵文件已包含"