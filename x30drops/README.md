<p align="center"><img src="docs/logo.png" width="160" alt="X30 Drops"></p>

# X30 Drops

Mod **Fabric** cho **Minecraft Java 26.2**: **bất cứ thứ gì rơi vật phẩm** đều rơi **gấp 30 lần**.

- **Khối:** đào đá ra 30 cobblestone, chặt gỗ ra 30 khúc gỗ, đào kim cương ra 30 viên...
- **Quái và động vật:** zombie, skeleton, bò, cừu... chết đều rơi gấp 30 lần.
- Cũng áp dụng cho khối/quái của mod khác, nổ TNT, piston, câu cá...
- Mỗi lần rơi được tách thành nhiều stack, không vượt quá giới hạn stack của vật phẩm.

## Chỉnh cấu hình
Mở `config/x30drops.properties` (tự tạo khi chạy game lần đầu), sửa rồi khởi động lại game:

```
multiplier=30
multiplyChests=false
```

- `multiplier`: số lần nhân, từ 1 đến 1000.
- `multiplyChests`: đặt `true` nếu muốn nhân luôn đồ trong rương hầm ngục khi mở lần đầu (mặc định tắt).

## Không bị nhân
- Đồ người chơi rơi khi chết.
- Món đồ quái đang mặc hoặc cầm (vd áo giáp zombie) khi chết, vì các món này không đi qua bảng loot.
- Điểm kinh nghiệm (XP).

## Cách cài
- **Chơi 1 người (singleplayer):** bỏ jar + Fabric API vào thư mục `mods` của client là chạy.
- **Chơi server:** phải cài vào **server** (client không cần). Chỉ cài ở client thì mod không có tác dụng.

## Yêu cầu
Minecraft Java 26.2, Fabric Loader ≥ 0.19.3, Fabric API cho 26.2, Java 25.

## Lưu ý
Nhiều vật phẩm rơi cùng lúc (nổ TNT hàng loạt, giết nhiều quái) có thể làm game lag.

## Build bằng GitHub
Đẩy toàn bộ lên GitHub, tab **Actions** → workflow **build** → tải jar ở mục **Artifacts**.

## Giấy phép
MIT
